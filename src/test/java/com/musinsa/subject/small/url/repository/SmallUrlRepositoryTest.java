package com.musinsa.subject.small.url.repository;

import com.musinsa.subject.TestDataInitRunner;
import com.musinsa.subject.small.url.domain.SmallUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class SmallUrlRepositoryTest {

    @Autowired
    SmallUrlRepository smallUrlRepository;

    @Test
    @DisplayName("SmallUrl을 저장할 수 있다.")
    void save() {
        // Given
        var expectedOriginalUrl = "https://www.musinsa.com/";
        var expectedHash = String.format("%08x", expectedOriginalUrl.hashCode());
        var smallUrl = new SmallUrl(expectedHash, expectedOriginalUrl);

        // When
        var savedSmallUrl = smallUrlRepository.save(smallUrl);

        //Then
        assertThat(savedSmallUrl).isNotNull();
        assertAll(
                () -> assertThat(savedSmallUrl.getHash()).isEqualTo(expectedHash),
                () -> assertThat(savedSmallUrl.getOriginalUrl()).isEqualTo(expectedOriginalUrl),
                () -> assertThat(savedSmallUrl.getRedirectCount()).isZero()
        );
    }

    @ParameterizedTest
    @DisplayName("잘못된 hash 혹은 orignalUrl을 포함한 SmallUrl은 저장할 수 없다")
    @CsvSource(
            value = {
                    "'','https://www.musinsa.com/'",
                    "' ','https://www.musinsa.com/'",
                    "'null','https://www.musinsa.com/'",
                    "'6af8ee04',''",
                    "'6af8ee04',' '",
                    "'6af8ee04','null'",
                    "'6af8ee04','notUrlString'",
            },
            nullValues = "null"
    )
    void saveInvalidHashSmallUrl(String hash, String originalUrl) {
        // Given
        var smallUrl = new SmallUrl(hash, originalUrl);

        // Expect
        assertThrows(
                ConstraintViolationException.class,
                () -> smallUrlRepository.save(smallUrl)
        );
    }

    @Test
    @DisplayName("hash값으로 SmallUrl을 찾을 수 있다")
    void findByHash() {
        var savedSmallUrl = TestDataInitRunner.testSmallUrl;
        // Given
        var expectedHash = savedSmallUrl.getHash();
        var expectedOriginalUrl = savedSmallUrl.getOriginalUrl();

        // When
        var optionalSmallUrl = smallUrlRepository.findByHash(expectedHash);

        // Then
        assertThat(optionalSmallUrl).isPresent();
        var smallUrl = optionalSmallUrl.get();
        assertThat(smallUrl.getHash()).hasSize(8).isNotBlank();
        assertThat(smallUrl.getOriginalUrl()).isEqualTo(expectedOriginalUrl);
        assertThat(smallUrl.getRedirectCount()).isGreaterThanOrEqualTo(0L);
    }

    @Test
    @DisplayName(" 존재하지 않는 hash값으로 SmallUrl을 찾을 수 없다")
    void findNotExistSmallUrlByHash() {
        // Given
        var expectedHash = "11111111";

        // When
        var optionalSmallUrl = smallUrlRepository.findByHash(expectedHash);

        // Then
        assertThat(optionalSmallUrl).isEmpty();
    }

}