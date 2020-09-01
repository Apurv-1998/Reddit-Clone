import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/common/post';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-user-post',
  templateUrl: './user-post.component.html',
  styleUrls: ['./user-post.component.css']
})
export class UserPostComponent implements OnInit {

  //stroing the post response
  postDetails: Post[] = [];

  //searching for keyword in the route
  searchMode: boolean;

  constructor(private activatedRoute:ActivatedRoute,
              private router: Router,
              private postService: PostService) { }

  ngOnInit(): void {

    this.activatedRoute.paramMap.subscribe(() =>{
      this.listOfPosts();
    });

  }


  listOfPosts(){

    this.searchMode = this.activatedRoute.snapshot.paramMap.has('userId');

    if(this.searchMode){
      this.showPosts();
    }
    else{
      this.router.navigateByUrl('');
    }
    

  }

  showPosts(){

    const userId = this.activatedRoute.snapshot.paramMap.get('userId');

    this.postService.getPostsOfUser(userId).subscribe(
      data => {
        console.log(`Post Data -> ${JSON.stringify(data)}`);
        this.postDetails = data;
      }

    );


  }

}
