import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-alarm-manager',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './alarm-manager.html',
  styleUrl: './alarm-manager.css'
})
export class AlarmManager {}