import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  //Email Regex
  EMAIL_REGEXP: string =  "/^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i";

  //Reactive Forms
  signupFormGroup: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    
    //The Groups formControlName(email,firstName etc.) are defined in the html
    this.signupFormGroup = this.formBuilder.group({
      email: ['',[Validators.required,Validators.pattern(this.EMAIL_REGEXP)]],
      firstName: ['',Validators.required],
      lastName: ['',Validators.required,],
      password: ['',Validators.required]
    });

  }

  onSubmit(){
    console.log("Handling Sign-up form");
    console.log('email value -> '+this.signupFormGroup.get('email').value);
    console.log('first name value -> '+this.signupFormGroup.get('firstName').value);
    console.log('last name value -> '+this.signupFormGroup.get('lastName').value);
  }

}
