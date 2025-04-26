import {Component, inject} from '@angular/core';
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {MatToolbar} from "@angular/material/toolbar";
import {HelpComponent} from "../help/help.component";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    MatToolbar,
    MatDialogModule,
    MatIcon,
    MatIconButton,

  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  private dialog = inject(MatDialog)

  help() {
    this.dialog.open(HelpComponent)
  }
}
