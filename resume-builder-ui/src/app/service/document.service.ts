import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UploadResponse } from '../model/model';


@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  private readonly apiUrl =
    'http://localhost:8080/api/v1/documents/upload';

  constructor(private http: HttpClient) {}

  upload(file: File): Observable<UploadResponse> {

    const formData = new FormData();

    formData.append('file', file);

    return this.http.post<UploadResponse>(
      this.apiUrl,
      formData
    );
  }
}