// question.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface QuestionDto {
  title: string;
  body: string;
  tags: string[];
  userId: number;
}

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  private apiUrl = 'http://localhost:8083/api/question'; 

  constructor(private http: HttpClient) {}

  postQuestion(question: QuestionDto): Observable<QuestionDto> {
    return this.http.post<QuestionDto>(this.apiUrl, question);
  }
}
