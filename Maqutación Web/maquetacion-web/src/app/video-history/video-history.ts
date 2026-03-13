import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-video-history',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './video-history.html',
  styleUrl: './video-history.css'
})
export class VideoHistory {}