package com.example.mreview.repository;

import com.example.mreview.entity.Movie;
import com.example.mreview.entity.MovieImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Commit // @Commit 은 테스트 방법이 완료된 후 테스트 관리 트랜잭션을 커밋해야 함을 나타내는 데 사용되는 테스트 주석입니다.
    @Transactional
    @Test
    @DisplayName("1. 영화와 영화이미지 를 추가하는 테스트 코드")
    public void insertMoviesTest(){

        IntStream.rangeClosed(1, 100).forEach(i ->{

            Movie movie = Movie.builder()
                    .title("Movie......" + i)
                    .build();

            System.out.println("------------------------------");

            movieRepository.save(movie);

            int count = (int)(Math.random() * 5)+1; // 1, 2, 3, 4

            for(int j = 0 ; j < count; j++){
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test"+j+".jpg")
                        .build();

                movieImageRepository.save(movieImage);
            }

            System.out.println("========================================");
        });

    }

    @DisplayName("2. 영화 리스트 조회 테스트")
    @Test
    public void movieListTest(){

        PageRequest pageRequest = PageRequest.of(0 , 10 , Sort.by(Sort.Direction.DESC , "mno"));

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for(Object[] objects : result.getContent()){

            System.out.println(Arrays.toString(objects));

        }

    }

    @DisplayName("3. 특정 영화 조회")
    @Test
    public void getMovieWithAllTest(){

        List<Object[]> result = movieRepository.getMovieWithAll(158L);

        System.out.println(result);

        for(Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }

//        System.out.println(">>> : " +  movieRepository.getMovieWithAll(157L).toArray().toString());


    }
}
