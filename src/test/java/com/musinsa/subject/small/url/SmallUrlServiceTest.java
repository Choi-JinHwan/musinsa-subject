package com.musinsa.subject.small.url;

import com.musinsa.subject.small.url.domain.SmallUrl;
import com.musinsa.subject.small.url.exception.SmallUrlNotFoundException;
import com.musinsa.subject.small.url.service.SmallUrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("SmallUrlService 테스트")
class SmallUrlServiceTest {

    @Autowired
    SmallUrlService smallUrlService;

    private SmallUrl savedSmallUrl;

    @BeforeEach
    void beforeEach() {
        var originalUrl = "https://www.wanted.co.kr/newintro";

        this.savedSmallUrl = smallUrlService.createSmallUrl(originalUrl);
    }

    @Test
    @DisplayName("SmallUrl을 생성할 수 있다.")
    void createTest() {
        // Given
        var originalUrl = "https://www.naver.com";

        // When
        var smallUrl = smallUrlService.createSmallUrl(originalUrl);

        // Then
        assertThat(smallUrl.getHash()).hasSize(8);
        assertThat(smallUrl.getOriginalUrl()).isEqualTo(originalUrl);
        assertThat(smallUrl.getRedirectCount()).isZero();
    }

    @ParameterizedTest
    @DisplayName("잘못된 orignalUrl로 SmallUrl을 생성할 수 없다")
    @ValueSource(strings = "notUrlString")
    @NullAndEmptySource
    void createInvalidSmallUrl(String originalUrl) {
        // expect
        assertThrows(
                ConstraintViolationException.class,
                () -> smallUrlService.createSmallUrl(originalUrl)
        );
    }

    @Test
    @DisplayName("같은 URL로 생성된 SmallUrl은 같은 hash를 가진다.")
    void sameUrlHasSameHash() {
        // Given
        var originalUrl = "https://www.naver.com";

        // When
        var smallUrlA = smallUrlService.createSmallUrl(originalUrl);
        var smallUrlB = smallUrlService.createSmallUrl(originalUrl);

        // Then
        assertThat(smallUrlA.getHash()).hasSize(8);
        assertThat(smallUrlA.getOriginalUrl()).isEqualTo(originalUrl);
        assertThat(smallUrlA.getRedirectCount()).isZero();

        assertThat(smallUrlB.getHash()).hasSize(8);
        assertThat(smallUrlB.getOriginalUrl()).isEqualTo(originalUrl);
        assertThat(smallUrlB.getRedirectCount()).isZero();

        assertThat(smallUrlA.getHash()).isEqualTo(smallUrlB.getHash());
    }

    @Test
    @DisplayName("다른 URL로 생성된 SmallUrl은 다른 hash를 가진다")
    void differentUrlHasDifferent() {
        // Given
        var urlA = "https://www.musinsa.com";
        var urlB = "https://www.daum.com";

        // When
        var smallUrlA = smallUrlService.createSmallUrl(urlA);
        var smallUrlB = smallUrlService.createSmallUrl(urlB);

        // Then
        assertThat(smallUrlA.getHash()).hasSize(8);
        assertThat(smallUrlA.getOriginalUrl()).isEqualTo(urlA);
        assertThat(smallUrlA.getRedirectCount()).isZero();

        assertThat(smallUrlB.getHash()).hasSize(8);
        assertThat(smallUrlB.getOriginalUrl()).isEqualTo(urlB);
        assertThat(smallUrlB.getRedirectCount()).isZero();

        assertThat(smallUrlA.getHash()).isNotEqualTo(smallUrlB.getHash());
    }

    @Test
    @DisplayName("hash로 기존에 저장한 SmallUrl#originalUrl을 가져올 수 있다")
    void findOriginalUrlByHash() {
        // Given
        var expectedHash = savedSmallUrl.getHash();
        var expectedOriginalUrl = savedSmallUrl.getOriginalUrl();

        // When
        var originalUrl = smallUrlService.findOriginalUrlByHashForRedirect(expectedHash);

        // Then
        assertThat(originalUrl).isEqualTo(expectedOriginalUrl);
    }

    @Test
    @DisplayName("존재하지 않는 hash로 SmallUrl#originalUrl을 가져올 수 없다")
    void findOriginalUrlByNotExistHash() {
        // Given
        var expectedHash = "11111111";

        // When
        assertThrows(
                SmallUrlNotFoundException.class,
                () -> smallUrlService.findOriginalUrlByHashForRedirect(expectedHash)
        );
    }

    @ParameterizedTest
    @DisplayName("유효하지 않은 hash로 SmallUrl#originalUrl을 가져올 수 없다")
    @ValueSource(strings = {"123456", "        "})
    @NullAndEmptySource
    void findOriginalUrlByInvalidHash(String invalidHash) {
        // When
        assertThrows(
                ConstraintViolationException.class,
                () -> smallUrlService.findOriginalUrlByHashForRedirect(invalidHash)
        );
    }


}