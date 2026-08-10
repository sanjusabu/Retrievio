import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { finalize } from 'rxjs';

import { ChatService } from './chat.service';

interface Message {
  role: 'user' | 'assistant';
  content: string;
}

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  question = '';

  loading = false;

  messages: Message[] = [];

  constructor(
    private chatService: ChatService,
    private cdr: ChangeDetectorRef
  ) {}

  sendMessage(): void {

    const question = this.question.trim();

    if (!question || this.loading) {
      return;
    }

    this.messages.push({
      role: 'user',
      content: question
    });

    this.question = '';
    this.loading = true;

    // Update UI immediately
    this.cdr.markForCheck();

    this.chatService
      .ask(question)
      .pipe(
        finalize(() => {
          this.loading = false;
          this.cdr.markForCheck();
        })
      )
      .subscribe({

        next: (response: string) => {

          console.log('Backend response:', response);

          this.messages.push({
            role: 'assistant',
            content: response
          });

          this.cdr.markForCheck();
        },

        error: (error) => {

          console.error('Chat API error:', error);

          this.messages.push({
            role: 'assistant',
            content: 'Sorry, something went wrong.'
          });

          this.cdr.markForCheck();
        }

      });
  }
}