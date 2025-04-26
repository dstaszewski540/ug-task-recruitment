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
  selector: 'app-item-bulk-delete',
  standalone: true,
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatDialogClose,
    MatDialogActions,
    MatButton
  ],
  templateUrl: './item-bulk-delete.component.html',
  styleUrl: './item-bulk-delete.component.scss'
})
export class ItemBulkDeleteComponent {
  readonly count = inject<number>(MAT_DIALOG_DATA)
  readonly ref = inject(MatDialogRef<ItemBulkDeleteComponent>);

  onNoClick() {
    this.ref.close(false);
  }
}
