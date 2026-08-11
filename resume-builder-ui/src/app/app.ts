import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { ChatService } from './service/chat.service';
import { DocumentService } from './service/document.service';
import { Message } from './model/model';
import { marked } from 'marked';

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

  uploading = false;

  resumeUploaded = false;

  uploadError = '';

  constructor(
    private chatService: ChatService,
    private documentService: DocumentService,
    private cdr: ChangeDetectorRef
  ) {}

  uploadResume(event: Event): void {

    const input = event.target as HTMLInputElement;

    if (!input.files || input.files.length === 0) {
      return;
    }

    const file = input.files[0];

    this.uploadError = '';

    // Only allow PDFs
    if (file.type !== 'application/pdf') {

      this.uploadError = 'Please upload a PDF file.';

      return;
    }

    this.uploading = true;

    this.documentService.upload(file).subscribe({

      next: (response) => {

        console.log('Upload response:', response);

        this.resumeUploaded = true;
        this.uploading = false;

        this.cdr.markForCheck();
      },

      error: (error) => {

        console.error('Upload error:', error);

        this.uploadError =
          'Failed to upload the resume. Please try again.';

        this.uploading = false;

        this.cdr.markForCheck();
      }

    });
  }


  sendMessage(): void {

    const question = this.question.trim();

    if (!question || this.loading || !this.resumeUploaded) {
      return;
    }

    this.messages.push({
      role: 'user',
      content: question
    });

    this.question = '';
    this.loading = true;

    this.cdr.markForCheck();

    this.chatService
      .ask(question)
      .subscribe({

        next: (response: string) => {


          this.messages.push({
            role: 'assistant',
            content: response
          });

          this.loading = false;

          this.cdr.markForCheck();
        },

        error: (error) => {

          console.error('Chat API error:', error);

          this.messages.push({
            role: 'assistant',
            content: 'Sorry, something went wrong.'
          });

          this.loading = false;

          this.cdr.markForCheck();
        }

      });
  }

  uploadNewResume(): void {

  this.resumeUploaded = false;

  this.messages = [];

  this.question = '';

  this.uploadError = '';

 }

 renderMarkdown(content: string): string {
  return marked.parse(content) as string;
}

}