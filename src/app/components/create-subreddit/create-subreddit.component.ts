import { Component, OnInit, destroyPlatform } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CreateSubreddit } from 'src/app/common/create-subreddit'; 
import { SubredditRestService } from 'src/app/services/subreddit-rest.service';

@Component({
  selector: 'app-create-subreddit',
  templateUrl: './create-subreddit.component.html',
  styleUrls: ['./create-subreddit.component.css']
})
export class CreateSubredditComponent implements OnInit {

  //Form Group Name
  formGroupName: FormGroup;

  //payload to be send as the request
  createSubredditPayload: CreateSubreddit;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private subredditService: SubredditRestService) { 


   this.createSubredditPayload = {
    subredditName: '',
    description: ''
   };


  }

  ngOnInit(): void {

    // Generating the form
    this.formGroupName = this.formBuilder.group({
      subredditName: ['', Validators.required],
      description: ['', Validators.required]
    });

  }


  //filling the payload

  onSubmit(){

    //handling the subreddit creation
    console.log('Handling the subreddit creation');
    console.log('Title: '+this.formGroupName.get('subredditName').value);
    console.log('Description: '+this.formGroupName.get('description').value);

    //Filling the payload

    this.createSubredditPayload.subredditName = this.formGroupName.get('subredditName').value;
    this.createSubredditPayload.description = this.formGroupName.get('description').value;

    //Getting the service

    const userId = this.activatedRoute.snapshot.paramMap.get('userId');

    this.subredditService.createNewSubreddit(this.createSubredditPayload,userId).subscribe(
      data =>{
        console.log('Creation Response: '+JSON.stringify(data));
        this.router.navigate(['/showAll']);
      }
    );

  }

  discard(){
    this.router.navigateByUrl('');
  }

}
