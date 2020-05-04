import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Observable, of, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, flatMap, switchMap, tap} from 'rxjs/operators';
import {Group} from '../../../../models/group';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-search-groups',
  templateUrl: './search-groups.component.html',
  styleUrls: ['./search-groups.component.css']
})
export class SearchGroupsComponent implements OnInit {

  @Output() searchResults = new EventEmitter<Group[]>();

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
          switchMap((term: string) => this.searchGroups(term))
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


  searchGroups(searchTerm: string): Observable<Group[]> {
    // if (!searchTerm.trim()) {
    //   return of([]);
    // }
    return this.http.get<Group[]>(`http://localhost:8080/api/groups/search?q=${searchTerm}`);
  }

  onSubmit() {
    this.search$.next();
  }
}
