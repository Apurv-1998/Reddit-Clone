import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CreatePostDetails } from 'src/app/common/create-post-details';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';
import { Post } from 'src/app/common/post';
import { ToastrService } from 'ngx-toastr';
import { SubredditRest } from 'src/app/common/subreddit-rest';
import { SubredditRestService } from 'src/app/services/subreddit-rest.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  //Form Group Name
  createPostForm: FormGroup;

  //Create Post Payload
  createPostPayload: CreatePostDetails;

  //Post Response
  createPostResponse: Post;

  //Getting the list of all subreddits
  subredditsList: SubredditRest[] = [];

  constructor(private formBuilder: FormBuilder,
              private router:Router,
              private activatedRoute: ActivatedRoute,
              private postService: PostService,
              private toaster: ToastrService,
              private subredditService: SubredditRestService) { 

    //filling the payload with default values
    this.createPostPayload = ({
      description: '',
      postName: '',
      subredditName: ''
    });

  }

  ngOnInit(): void {

    this.createPostForm = this.formBuilder.group({
      subredditName: ['',Validators.required],
      postName: ['',Validators.required],
      description: ['',Validators.required]
    });

    //Getting the list of all subreddits
    this.subredditService.getAllSubreddits().subscribe(
      data => {
        this.subredditsList = data;
        console.log('Subreddits List -> '+JSON.stringify(data));
      }
    );

  }


  onSubmit() {

    //Handling the post data
    console.log('Post Data');
    console.log('Subreddit Name -> '+this.createPostForm.get('subredditName').value);
    console.log('Post Name -> '+this.createPostForm.get('postName').value);
    console.log('Post Description -> '+this.createPostForm.get('description').value);

    //Assigning the info from form to the payload
    this.createPostPayload.subredditName = this.createPostForm.get('subredditName').value;
    this.createPostPayload.postName = this.createPostForm.get('postName').value;
    this.createPostPayload.description = this.createPostForm.get('description').value;

    //Calling the service and creating the post
    this.postService.createNewPost(this.createPostPayload).subscribe(
      data => {
        console.log('Data Received -> '+JSON.stringify(data));
        this.createPostResponse = data;
        this.toaster.success('New Post Created');
      },
      error =>{
        this.router.navigateByUrl('/createPosts');
        this.toaster.warning('New Post Nost Created');
      }
    );

  }

  discard() {
    this.router.navigateByUrl('');
  }

}
