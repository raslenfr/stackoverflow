import { Component, inject } from '@angular/core'; 
import { QuestionService } from '../../user-services/question-service/question.service';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatChipEditedEvent, MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';

@Component({
  selector: 'app-post-question',
  templateUrl: './post-question.component.html',
  styleUrls: ['./post-question.component.scss']
})
export class PostQuestionComponent {
  isSubmitting: boolean = false;
  addOnBlur: boolean = true;
  validateForm!: FormGroup;

  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  announcer = inject(LiveAnnouncer);
  
  constructor(private service: QuestionService, private fb: FormBuilder) {}

  ngOnInit() {
    // Initialize the form with title, body, tags array, and userId
    this.validateForm = this.fb.group({
      title: ['', Validators.required],
      body: ['', Validators.required],
      tags: this.fb.array([], Validators.required),
      userId: [1, Validators.required]  // Adjust the userId value as needed for actual use
    });
  }

  get tagsFormArray(): FormArray {
    return this.validateForm.get('tags') as FormArray;
  }

  // Method to add a tag
  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value) {
      // Add a new tag to the FormArray
      this.tagsFormArray.push(this.fb.control(value));  
    }
    event.chipInput!.clear();
  }

  // Method to remove a tag
  remove(index: number): void {
    // Remove tag from FormArray
    this.tagsFormArray.removeAt(index);
    this.announcer.announce(`Removed tag at index ${index}`);
  }

  // Method to edit a tag
  edit(index: number, event: MatChipEditedEvent): void {
    const value = event.value.trim();
    if (!value) {
      this.remove(index);
      return;
    }
    // Update the value in the FormArray
    this.tagsFormArray.at(index).setValue(value);
  }

  postQuestion() {
    if (this.validateForm.valid) {
      this.isSubmitting = true; // Show spinner
      const questionData = this.validateForm.value;
  
      this.service.postQuestion(questionData).subscribe({
        next: response => {
          console.log('Question posted successfully', response);
          this.isSubmitting = false;
          this.validateForm.reset();
          this.tagsFormArray.clear();  // Clear tags after successful post
        },
        error: error => {
          console.error('Error posting question', error);
          this.isSubmitting = false;
        }
      });
    }
  }
}
