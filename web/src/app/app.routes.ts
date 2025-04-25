import {Routes} from '@angular/router';
import {ViewComponent} from "./route/view/view.component";
import {ErrorComponent} from "./route/error/error.component";

export const routes: Routes = [
  {
    path: '',
    component: ViewComponent
  },
  {
    path: 'error',
    component: ErrorComponent
  }
];
