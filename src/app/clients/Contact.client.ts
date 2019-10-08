import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Contact} from '../models/Contact.model';
import {Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {Injectable} from '@angular/core';

@Injectable()
export class ContactClient {
  private serverUrl = 'http://localhost:8080/contacts';
  private retryNb = 0;

  private optionRequete = {
    headers: new HttpHeaders({
      'Access-Control-Allow-Origin': '*'
    })
  };

  constructor(private http: HttpClient) {}

  private static handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError( 'Something bad happened; please try again later.');
  }

  create(contact: Contact): Promise<Contact> {
    return this.http.post<Contact>(this.serverUrl, contact, this.optionRequete).pipe(
      retry(this.retryNb),
      catchError(ContactClient.handleError)
    ).toPromise();
  }

  readAll(): Promise<Contact[]> {
    return this.http.get<Contact[]>(this.serverUrl).pipe(
      retry(this.retryNb),
      catchError(ContactClient.handleError)
    ).toPromise();
  }

  update(contact: Contact): Promise<Contact> {
    return this.http.put<Contact>(this.serverUrl, contact, this.optionRequete).pipe(
      retry(this.retryNb),
      catchError(ContactClient.handleError)
    ).toPromise();
  }

  delete(id: number): Observable<{}> {
    const url = this.serverUrl + '/' + id;
    return this.http.delete(url).pipe(
      retry(this.retryNb),
      catchError(ContactClient.handleError)
    );
  }
}
