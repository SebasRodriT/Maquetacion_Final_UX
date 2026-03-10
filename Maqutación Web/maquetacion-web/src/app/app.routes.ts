import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';
import { Alarms } from './alarms/alarms';

export const routes: Routes = [
  { path: '', component: Dashboard },
  { path: 'alarms', component: Alarms },
];
