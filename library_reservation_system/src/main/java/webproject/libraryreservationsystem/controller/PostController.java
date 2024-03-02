package webproject.libraryreservationsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import webproject.libraryreservationsystem.domain.*;
import webproject.libraryreservationsystem.service.AnnouncementService;
import webproject.libraryreservationsystem.service.CommentService;
import webproject.libraryreservationsystem.service.PostService;
import webproject.libraryreservationsystem.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final AnnouncementService announcementService;
    @GetMapping("/posts")
    public String posts(Model model){
        List<Post> posts = postService.getPosts();
        model.addAttribute("postList",posts);
        return "posts";
    }

    @GetMapping("/writepost")
    public String writePost(){
        return "writepost";
    }

    @PostMapping("/writepost")
    public String writePost(PostForm postForm, Principal principal) {
        Optional<SiteUser> user = userService.getUser(principal.getName());
        if (user.isEmpty()){
            return "redirect:/posts";
        }
        postService.saveWritedPost(postForm,user.get());
        return "redirect:/posts";
    }

    @GetMapping("/posts/detail/{id}")
    public String getDetail(@PathVariable("id") Long id,Model model){
        Optional<Post> post = postService.getPost(id);
        if (post.isEmpty()) return "redirect:/posts";
        model.addAttribute("post",post.get());
        List<Comment> allComment = commentService.getAllComment(id);
        model.addAttribute("comments",allComment);
        return "postdetail";
    }

    @PostMapping("/writecomment/{id}")
    public String writeComment(@PathVariable("id") Long id, CommentForm commentForm, Principal principal){
        Optional<SiteUser> user = userService.getUser(principal.getName());
        Comment comment = new Comment(user.get().getUserName(), commentForm.getContent(), id);
        commentService.saveComment(comment);
        return ("redirect:/posts/detail/" + id);
    }

    @GetMapping("/announcements")
    public String announcements(Model model){
        List<Announcement> announcements = announcementService.getAnnouncements();
        model.addAttribute("announcements",announcements);
        return "announcement";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/writeannouncement")
    public String writeAnnouncement(){
        return "writeannouncement";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("writeannouncement")
    public String writeAnnouncement(Principal principal, AnnouncementForm announcementForm){
        Optional<SiteUser> user = userService.getUser(principal.getName());
        if (user.isEmpty()){
            return "redirect:/announcements";
        }
        announcementService.saveAnnouncement(announcementForm,user.get());
        return "redirect:/announcements";
    }

    @GetMapping("/announcements/detail/{id}")
    public String getAnnouncementDetail(@PathVariable("id") Long id,Model model){
        Optional<Announcement> announcement = announcementService.getAnnouncement(id);
        if (announcement.isEmpty()) return "redirect:/announcements";
        model.addAttribute("announcement",announcement.get());
        return "announcementdetail";
    }
}
