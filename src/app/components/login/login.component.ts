import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Login } from 'src/app/common/login';
import { LoginService } from 'src/app/services/login.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  //Signin Form Group
  loginformGroup: FormGroup;

  //Getting the login class -> payload
  loginUser: Login;

  //Toaster Messages
  registerSuccessMessage: string;
  isError: boolean;

  constructor(private formBuilder: FormBuilder,
              private loginService: LoginService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private toaster: ToastrService) { }

  ngOnInit(): void {

    //Creating the formGroup
    this.loginformGroup = this.formBuilder.group({
      firstName: ['',Validators.required],
      lastName: ['',Validators.required],
      password: ['',Validators.required]
    });

    //Initializing the loginUser -> Payload
    this.loginUser = {
      firstName: '',
      lastName: '',
      password: ''
    };

     //Checking Whether Registration is successful
     this.activatedRoute.queryParams.subscribe(
       params => {
         if(params.registered !== undefined && params.registered === 'true'){
           this.toaster.success("Signup Successful");
           this.registerSuccessMessage = "Please Check You Inbox for activation email"
                                          +" Register Before Login!";
         }
       }
     );

  }

  onSubmit(){
    
    //Logging The Form Details
    console.log('Handling Login Form');
    console.log('First Name -> '+this.loginformGroup.get('firstName').value);
    console.log('Last Name -> '+this.loginformGroup.get('lastName').value);

    //Setting the value of the payload from the form
    this.loginUser.firstName = this.loginformGroup.get('firstName').value;
    this.loginUser.lastName = this.loginformGroup.get('lastName').value;
    this.loginUser.password = this.loginformGroup.get('password').value;

    //Subscribing to the data
    this.loginService.loginUser(this.loginUser).subscribe(
      data => {
        this.isError = false;
        this.router.navigate(['/showPosts']);
        this.toaster.success('Login Successful');
      }, error => {
        this.isError = true;
        throwError(error);
      }
    );

  }

}
