import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PurchasesService {

  constructor(
    private http: HttpClient,
  ) { }

  getPurchaseList(){
    const url = environment.apiEndPoint + '/purchase';

    return this.http.get(url)
      .pipe(
        map(purchase =>{
          return purchase;
        }),
        catchError(error => {
          return throwError(error);
        })
      );
  }

  getPurchaseLastYearList(){
    const url = environment.apiEndPoint + '/purchase/lastYearByMonth';

    return this.http.get(url)
      .pipe(
        map(purchaseList =>{
          return purchaseList;
        }),
        catchError(error => {
          return throwError(error);
        })
      );
  }

  postPurchase(id:number){
    const headers = new HttpHeaders({
        'Content-Type': 'application/json',
    });

    const url = environment.apiEndPoint + '/purchase/' + id

    return this.http.post(url,{headers})
    .pipe(
      map(purchaseSend =>{
        return purchaseSend;
      }),
      catchError(error => {
        return throwError(error);
      })
    );
  }
}
