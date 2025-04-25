import {Component, inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-item-delete',
  standalone: true,
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatDialogClose,
    MatDialogActions,
    MatButton
  ],
  templateUrl: './item-delete.component.html',
  styleUrl: './item-delete.component.scss'
})
export class ItemDeleteComponent {
  readonly name = inject<string>(MAT_DIALOG_DATA);
  readonly ref = inject(MatDialogRef<ItemDeleteComponent>);

  onNoClick() {
    this.ref.close(false);
  }
}
