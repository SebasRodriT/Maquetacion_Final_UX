import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';
import { Alarms } from './alarms/alarms';
import { AlarmManager } from './alarm-manager/alarm-manager';
import { VideoHistory } from './video-history/video-history';

export const routes: Routes = [
  { path: '', component: Dashboard },
  { path: 'alarms', component: Alarms },
  { path: 'alarm-manager', component: AlarmManager },
  { path: 'video-history', component: VideoHistory },
];
