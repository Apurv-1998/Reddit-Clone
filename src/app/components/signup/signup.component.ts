import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SignupUser } from 'src/app/common/signup-user';
import { SignupServiceService } from 'src/app/services/signup-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  //Reactive Forms
  signupFormGroup: FormGroup;

  //Sending the Request to signup the user
  signupUser: SignupUser;


  //constructor
  constructor(private formBuilder: FormBuilder,
              private signupService: SignupServiceService,
              private route: ActivatedRoute,
              private toaster: ToastrService,
              private router: Router) 
            { 

    //Providing empty string to the payload
    this.signupUser = {
      email: '',
      firstName: '',
      lastName: '',
      password: ''
    };

  }

  ngOnInit(): void {
    
    //The Groups formControlName(email,firstName etc.) are defined in the html
    this.signupFormGroup = this.formBuilder.group({
      email: ['',[Validators.required,Validators.email]],
      firstName: ['',Validators.required],
      lastName: ['',Validators.required,],
      password: ['',Validators.required]
    });

   

  }

  onSubmit(){
    /* Checking The Console*/
    console.log("Handling Sign-up form");
    console.log('email value -> '+this.signupFormGroup.get('email').value);
    console.log('first name value -> '+this.signupFormGroup.get('firstName').value);
    console.log('last name value -> '+this.signupFormGroup.get('lastName').value);

    /* Passing The Inputs From The Form To The Payload */
    this.signupUser.email = this.signupFormGroup.get('email').value;
    this.signupUser.firstName = this.signupFormGroup.get('firstName').value;
    this.signupUser.lastName = this.signupFormGroup.get('lastName').value;
    this.signupUser.password = this.signupFormGroup.get('password').value;

    /* Passing the payload to the serivce class */
    this.signupService.signUpUser(this.signupUser).subscribe(
      data => {
        this.router.navigate(['/login'],
          {queryParams: { registered: 'true'}}); //this will help to notify the loginComponent that registration is successful
      }, error => {
        console.log(error);
        this.toaster.error("Registration Failed! Please Try Again");
      }
    );
  }

}
