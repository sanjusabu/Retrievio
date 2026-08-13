import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { ChatService } from './service/chat.service';
import { DocumentService } from './service/document.service';
import { Message } from './model/model';
import { marked } from 'marked';
import { finalize } from 'rxjs/internal/operators/finalize';

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

  docUploaded = false;

  uploadError = '';

  documentId: string | null = null;

  deletingDocument = false;

  constructor(
    private chatService: ChatService,
    private documentService: DocumentService,
    private cdr: ChangeDetectorRef
  ) {}

  uploadDocument(event: Event): void {

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

        this.documentId = response.documentId;
        this.docUploaded = true;
        this.uploading = false;

        this.cdr.markForCheck();
      },

      error: (error) => {

        console.error('Upload error:', error);

        this.uploadError =
          'Failed to upload the document. Please try again.';

        this.uploading = false;

        this.cdr.markForCheck();
      }

    });
  }


  sendMessage(): void {

    const question = this.question.trim();

    if (!question || this.loading || !this.docUploaded) {
      return;
    }

    const history = this.messages.slice(-6);

    this.messages.push({
      role: 'user',
      content: question
    });

    this.question = '';
    this.loading = true;

    this.cdr.markForCheck();

    this.chatService
      .ask(question, history)
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

uploadNewDoc(): void {
  this.deletingDocument = true;

  this.documentService
    .deleteChunks(this.documentId!)
    .subscribe({
      next: (response) => {
        this.resetDocumentState();
      },
      error: (error) => {
        console.error('Failed to delete chunks:', error);
        this.resetDocumentState();
      }
    });
}

private resetDocumentState(): void {
  this.deletingDocument = false;
  this.docUploaded = false;
  this.messages = [];
  this.question = '';
  this.documentId = null;
  this.uploadError = '';
  this.cdr.detectChanges();
}

 renderMarkdown(content: string): string {
  return marked.parse(content) as string;
}

}