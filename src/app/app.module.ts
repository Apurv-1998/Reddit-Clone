import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { Routes,RouterModule } from '@angular/router';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { EditorModule } from '@tinymce/tinymce-angular';


import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { UserPostComponent } from './components/user-post/user-post.component';
import { PostTileComponent } from './components/post-tile/post-tile.component';
import { VoteButtonComponent } from './components/vote-button/vote-button.component';
import { SideBarComponent } from './components/side-bar/side-bar.component';
import { SubredditSideBarComponent } from './components/subreddit-side-bar/subreddit-side-bar.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { CreateSubredditComponent } from './components/create-subreddit/create-subreddit.component';
import { ShowSubredditsComponent } from './components/show-subreddits/show-subreddits.component';


//Defining the routes

const routes: Routes = [
    {path: 'createPost', component: CreatePostComponent},
    {path: 'createSubreddit/:userId', component: CreateSubredditComponent},
    {path: 'showAll', component: ShowSubredditsComponent},
    {path: 'showPosts/:userId', component: UserPostComponent},
    {path: 'login', component: LoginComponent},
    {path: 'users/signup', component: SignupComponent}
  
];


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignupComponent,
    LoginComponent,
    UserPostComponent,
    PostTileComponent,
    VoteButtonComponent,
    SideBarComponent,
    SubredditSideBarComponent,
    CreatePostComponent,
    CreateSubredditComponent,
    ShowSubredditsComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FontAwesomeModule,
    EditorModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
