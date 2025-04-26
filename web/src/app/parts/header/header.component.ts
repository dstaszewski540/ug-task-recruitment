import {Component} from '@angular/core';
import {MatDialogModule} from "@angular/material/dialog";
import {MatToolbar} from "@angular/material/toolbar";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    MatToolbar,
    MatDialogModule,

  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
}
