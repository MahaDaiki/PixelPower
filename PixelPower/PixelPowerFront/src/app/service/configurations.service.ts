import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ConfigurationsService {
  private apiUrl = 'http://localhost:8080/api/configurations';

  constructor(private http: HttpClient) {}

  getConfigurations(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

  addConfiguration(configuration: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, configuration);
  }


  updateConfiguration(id: string, updatedConfig: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, updatedConfig);
  }

  deleteConfiguration(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getConfigurationById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }



}
