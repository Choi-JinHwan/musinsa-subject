package com.musinsa.subject.small.url.interfaces;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.subject.TestDataInitRunner;
import com.musinsa.subject.small.url.domain.SmallUrl;
import com.musinsa.subject.small.url.vo.SmallUrlCreateRequest;
import com.musinsa.subject.web.SuccessResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SmallUrlControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createSmallUrl() throws Exception {
        // Given
        var expectedOriginalUrl = "https://www.musinsa.com";
        var smallUrlCreateRequest = new SmallUrlCreateRequest(expectedOriginalUrl);

        // When
        var mvcResult = mockMvc.perform(
                post("/small-url")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(smallUrlCreateRequest))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then
        var response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<SuccessResponse<SmallUrl>>() {
                }
        ).getData();

        assertThat(response).isNotNull();
        assertThat(response.getOriginalUrl()).isEqualTo(expectedOriginalUrl);
        assertThat(response.getRedirectCount()).isZero();
        assertThat(response.getHash()).hasSize(8);
    }

    @ParameterizedTest
    @DisplayName("잘못된 orignalUrl로 SmallUrl을 생성할 수 없다")
    @ValueSource(strings = "notUrlString")
    @NullAndEmptySource
    void createInvalidSmallUrl(String invalidOriginalUrl) throws Exception {
        // Given
        var smallUrlCreateRequest = new SmallUrlCreateRequest(invalidOriginalUrl);

        // When
        mockMvc.perform(
                post("/small-url")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(smallUrlCreateRequest))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("hash로 SmallUrl을 찾아 SmallUrl#originalUrl로 리다이렉션할 수 있다")
    void redirect() throws Exception {
        // Given
        var savedSmallUrl = TestDataInitRunner.testSmallUrl;
        var hash = savedSmallUrl.getHash();
        var expectedUrl = savedSmallUrl.getOriginalUrl();

        // When
        var mvcResult = mockMvc.perform(
                get("/" + hash)
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader("Location")).isEqualTo(expectedUrl);
    }

    @Test
    @DisplayName("존재하지 않는 hash로 SmallUrl을 찾아 SmallUrl#originalUrl로 리다이렉션할 수 없다")
    void redirectNotExistUrl() throws Exception {
        // Given
        var hash = "11111111";

        // When
        mockMvc.perform(
                get("/" + hash)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

}