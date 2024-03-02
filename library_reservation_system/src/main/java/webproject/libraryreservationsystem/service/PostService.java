package webproject.libraryreservationsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webproject.libraryreservationsystem.domain.Post;
import webproject.libraryreservationsystem.domain.PostForm;
import webproject.libraryreservationsystem.domain.SiteUser;
import webproject.libraryreservationsystem.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    public List<Post> getPosts(){
        List<Post> all = postRepository.findAll();
        return all;
    }

    public Optional<Post> getPost(Long id){
        Optional<Post> post = postRepository.findById(id);
        return post;
    }

    public Post saveWritedPost(PostForm postForm, SiteUser siteUser){
        Post post = new Post(siteUser.getUserName(),postForm.getTitle(),postForm.getContent(), LocalDateTime.now());
        postRepository.save(post);
        return post;
    }
}
