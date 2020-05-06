import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {FlexLayoutModule} from '@angular/flex-layout';

import {MatToolbarModule} from '@angular/material/toolbar';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatDividerModule} from '@angular/material/divider';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatCardModule} from '@angular/material/card';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSelectModule} from '@angular/material/select';
import {MatNativeDateModule, MatOptionModule} from '@angular/material/core';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { LoginComponent } from './components/login/login.component';
import { AlertComponent } from './components/alert/alert.component';
import { RegisterComponent } from './components/register/register.component';
import {JwtInterceptor} from './services/jwt.interceptor';
import { UsersComponent } from './components/users/users.component';
import { PostsComponent } from './components/posts/posts.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { UploadFileComponent } from './components/upload-file/upload-file.component';
import { HomeComponent } from './components/home/home.component';
import { HomeGroupsComponent } from './components/home/home-groups/home-groups.component';
import { HomeGroupComponent } from './components/home/home-group/home-group.component';
import { HomePostsComponent } from './components/home/home-posts/home-posts.component';
import { HomePostComponent } from './components/home/home-post/home-post.component';
import { SearchGroupsComponent } from './components/home/home-groups/search-groups/search-groups.component';
import { SearchPostsComponent } from './components/home/home-posts/search-posts/search-posts.component';
import { GroupDetailsComponent } from './components/group-details/group-details.component';
import { UploadGroupImageComponent } from './components/group-details/upload-group-image/upload-group-image.component';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    LoginComponent,
    AlertComponent,
    RegisterComponent,
    UsersComponent,
    PostsComponent,
    UploadFileComponent,
    HomeComponent,
    HomeGroupsComponent,
    HomeGroupComponent,
    HomePostsComponent,
    HomePostComponent,
    SearchGroupsComponent,
    SearchPostsComponent,
    GroupDetailsComponent,
    UploadGroupImageComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        HttpClientModule,

        // MATERIAL IMPORTS
        MatToolbarModule,
        MatMenuModule,
        MatIconModule,
        MatButtonModule,
        MatTableModule,
        MatDividerModule,
        MatProgressSpinnerModule,
        MatInputModule,
        MatCardModule,
        MatSlideToggleModule,
        MatSelectModule,
        MatOptionModule,

        MatNativeDateModule,

        // FlexLayoutModule
        FlexLayoutModule,

        BrowserAnimationsModule,
        FormsModule,
        MatDatepickerModule
    ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
