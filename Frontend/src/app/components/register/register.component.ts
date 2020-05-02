import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/authentication.service';
import {AlertService} from '../../services/alert.service';
import {first} from 'rxjs/operators';
import {MustMatch} from './validators/must-match.validator';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  usernameExists = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      gender: ['', Validators.required]
      // birthdate: ['', Validators.required]
    },
      {
        validator: MustMatch('password', 'confirmPassword')
      });

    this.registerForm.get('gender').setValue('M');
    this.registerForm.get('username').setValue('FisnikL');
    this.registerForm.get('firstName').setValue('Fisnik');
    this.registerForm.get('lastName').setValue('Limani');
    this.registerForm.get('email').setValue('fisnik.limani1@gmail.com');
    this.registerForm.get('password').setValue('Fisnik');
    this.registerForm.get('confirmPassword').setValue('Fisnik');
    // Date date = new Date(this.registerForm.get('birthdate').value);
  }

  // Convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }

  onSubmit() {
    this.submitted = true;

    // reset alerts on submit
    this.alertService.clear();
    // stop here if form is invalid
    if (this.registerForm.invalid) {
      // console.log(this.registerForm);
      return;
    }

    console.log(this.registerForm.get('username'));

    this.http.post<boolean>('http://localhost:8080/api/users/exists', {username: this.registerForm.get('username').value})
      .subscribe(
        response => {
          if (response === true) {
            // console.log(true);
            this.usernameExists = true;
            this.alertService.error(`Username "${this.registerForm.get('username').value}" is already taken`);
            this.loading = false;
          } else {
            console.log(false);
            this.loading = true;
            console.log(this.registerForm.value);
            this.http.post<string>(`http://localhost:8080/api/users/register`, this.registerForm.value)
              .pipe(first())
              .subscribe(
                data => {
                  console.log(data);
                  console.log('success');
                  this.alertService.success('Registration successful', true);
                  this.router.navigate(['/login']);
                },
                error => {
                  console.log('error');
                  this.alertService.error(error);
                  this.loading = false;
                }
              );
          }
        }
      );

  }
}
