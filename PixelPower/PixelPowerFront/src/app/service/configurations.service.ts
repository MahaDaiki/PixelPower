import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import Configuration = jasmine.Configuration;

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

  updateConfiguration(id: string, updatedConfig: Configuration): Observable<Configuration> {
    return this.http.put<Configuration>(`${this.apiUrl}/${id}`, updatedConfig);
  }


  deleteConfiguration(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }


}
