import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-alarms',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './alarms.html',
  styleUrl: './alarms.css'
})
export class Alarms {}
