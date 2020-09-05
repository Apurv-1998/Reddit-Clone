import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PostService } from 'src/app/services/post.service';
import { Post } from 'src/app/common/post';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CreateComment } from 'src/app/common/create-comment';
import { CommentServiceService } from 'src/app/services/comment-service.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {

  //Take in the post Response
  postRest: Post;

  //Search whether the postId is in the url or not
  searchMode: boolean;

  //Getting the userId and the PostId
  postId: string;
  userId: string;

  //Comment Form
  commentForm: FormGroup

  //Comment Payload
  commentPayload: CreateComment;


  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private postService: PostService,
              private formBuilder: FormBuilder,
              private commentService: CommentServiceService) { 

    //Initializing the comment payload
    this.commentPayload = ({
      text: ''
    });

  }

  ngOnInit(): void {

    //Creating the comment form

    this.commentForm = this.formBuilder.group({

      text: ['',Validators.required]

    });

    //subscribing to the activated route
    this.activatedRoute.paramMap.subscribe(
      () => {
        this.showAllPosts();
      }
    );

    

  }

  showAllPosts() {

    this.searchMode = this.activatedRoute.snapshot.paramMap.has('postId');

    if(this.searchMode){
      this.listAllPosts();
    }
    else{
      this.router.navigateByUrl('');
    }
  }

  listAllPosts() {

    const postId = this.activatedRoute.snapshot.paramMap.get('postId');

    this.postService.getSpecificPostDetails(postId).subscribe(
      data => {
        this.postRest = data;
        console.log("Particular Post Data -> "+JSON.stringify(data));
      }
    );
  }

  /*            Comment Section       */

  //On Submit button wil use these postId and the userId to fetch the data from backend

  onSubmit(){
    this.postId = this.postRest.postId;
    this.userId = this.postRest.userId;

    //Handling Comment Form
    console.log('Handling The Comment Form');
    console.log('Comment Made -> '+this.commentForm.get('text').value);
    console.log(`postId -> ${this.postId}, userId -> ${this.userId}`);

    //Populating the payload
    this.commentPayload.text = this.commentForm.get('text').value;


    //Calling the comment Service
    this.commentService.createComment(this.postId,this.userId,this.commentPayload).subscribe(
      data => {
        console.log('Data Received, '+JSON.stringify(data));
      }
    );

  }
  

}
