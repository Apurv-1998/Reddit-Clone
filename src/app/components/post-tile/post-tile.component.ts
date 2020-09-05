import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
import { Router, ActivatedRoute } from '@angular/router';
import { faArrowUp, faArrowDown, faComments } from '@fortawesome/free-solid-svg-icons';
import { Post } from 'src/app/common/post';

@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css']
})
export class PostTileComponent implements OnInit {

  //fa-icons
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  faComments = faComments;

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

  viewPost(postId: string) {

    this.router.navigate(['/viewPosts/'+postId]);

  }

}
