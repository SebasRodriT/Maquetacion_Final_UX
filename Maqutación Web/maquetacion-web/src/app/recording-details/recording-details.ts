import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-recording-details',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './recording-details.html',
  styleUrl: './recording-details.css'
})
export class RecordingDetails {}