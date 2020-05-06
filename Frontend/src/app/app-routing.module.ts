import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {UsersComponent} from './components/users/users.component';
import {PostsComponent} from './components/posts/posts.component';
import {UploadFileComponent} from './components/upload-file/upload-file.component';
import {HomeComponent} from './components/home/home.component';
import {GroupDetailsComponent} from './components/group-details/group-details.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'users', component: UsersComponent},
  {path: 'posts', component: PostsComponent},
  {path: 'upload', component: UploadFileComponent},
  {path: 'home', component: HomeComponent},
  {path: 'groups/:code', component: GroupDetailsComponent},
  // otherwise redirect to login
  {path: '**', redirectTo: 'home'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
