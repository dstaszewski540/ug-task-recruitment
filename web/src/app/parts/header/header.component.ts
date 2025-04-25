import {Component, inject} from '@angular/core';
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {MatToolbar} from "@angular/material/toolbar";
import {ItemFormComponent, ItemFormResult} from "../item-form/item-form.component";
import {HttpClient} from "@angular/common/http";

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
