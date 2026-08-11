export interface UploadResponse {
  documentId: string;
  size: number;
}

export interface ChatRequest {
  request: string;
}

export interface Message {
  role: 'user' | 'assistant';
  content: string;
}