import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Observable, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Post} from '../../../../models/post';
import {debounceTime, distinctUntilChanged, flatMap, switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-search-posts',
  templateUrl: './search-posts.component.html',
  styleUrls: ['./search-posts.component.css']
})
export class SearchPostsComponent implements OnInit {

  @Output() searchResults = new EventEmitter<Post[]>();

  searchForm = new FormGroup(
    {
      searchTerm: new FormControl('')
    }
  );

  search$ = new Subject();

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.search$.pipe(
      flatMap(
        () => this.searchForm.controls.searchTerm.valueChanges.pipe(
          debounceTime(300),
          distinctUntilChanged(),
          switchMap((term: string) => this.searchPosts(term))
        )
      )
    ).subscribe(
      result => {
        this.searchResults.emit(result);
        console.log(result);
      }
    );
    this.search$.next();
  }

  searchPosts(searchTerm: string): Observable<Post[]> {
    // if (!searchTerm.trim()) {
    //   return of([]);
    // }
    return this.http.get<Post[]>(`http://localhost:8080/api/posts/search?q=${searchTerm}`);
  }

  onSubmit() {
    this.search$.next();
  }
}
