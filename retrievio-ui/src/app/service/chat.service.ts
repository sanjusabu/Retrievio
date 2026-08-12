import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChatMessage, ChatRequest } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private readonly apiUrl =
    'http://localhost:8080/api/v1/chat/ask';

  constructor(private http: HttpClient) {}

  ask(question: string, history: ChatMessage[]): Observable<string> {

    const body: ChatRequest = {
      request: question,
      history: history
    };

    return this.http.post(
      this.apiUrl,
      body,
      {
        responseType: 'text'
      }
    );
  }
}