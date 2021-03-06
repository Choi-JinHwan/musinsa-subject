package com.musinsa.subject.small.url.interfaces;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.subject.SuccessResponse;
import com.musinsa.subject.small.url.domain.SmallUrl;
import com.musinsa.subject.small.url.vo.SmallUrlCreateRequest;
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

}