import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { ApiService } from '../api.service';
import { map } from 'rxjs/internal/operators';

@Injectable({
  providedIn: 'root'
})
export class IssueHistoryService {
  ISSUE_HISTORY_PATH = '/issue/history';
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  constructor(private apiService: ApiService, private http: HttpClient) { }

  getAllPageable(page): Observable<any> {
    return this.apiService.get(this.ISSUE_HISTORY_PATH + '/pagination', page).pipe(map(
      res => {
        if (res) {
          return res;
        } else {
          return {};
        }
      }
    ));
  }

  getAll(): Observable<any> {
    return this.apiService.get(this.ISSUE_HISTORY_PATH).pipe(map(
      res => {
        if (res) {
          return res;
        } else {
          return {};
        }
      }
    ));
  }

  getById(id): Observable<any> {
    return this.apiService.get(this.ISSUE_HISTORY_PATH, id).pipe(map(
      res => {
        if (res) {
          return res;
        } else {
          console.log(res);
          return {};
        }
      }
    ));
  }

  createIssue(issue): Observable<any> {
    return this.apiService.post(this.ISSUE_HISTORY_PATH, issue).pipe(map(
      res => {
        if (res) {
          return res;
        } else {
          console.log(res);
          return {};
        }
      }
    ));
  }

  delete(id): Observable<any> {
    return this.apiService.delete(this.ISSUE_HISTORY_PATH + '/' + id).pipe(map(
      res => {
        if (res) {
          return res;
        } else {
          console.log(res);
          return {};
        }
      }
    ));
  }

}
