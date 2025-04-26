import {Component, inject} from '@angular/core';
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";
import {
  MatCell,
  MatCellDef,
  MatColumnDef, MatHeaderCell, MatHeaderCellDef, MatHeaderRow, MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from "@angular/material/table";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-help',
  standalone: true,
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatButton,
    MatTable,
    MatCellDef,
    MatCell,
    MatIcon,
    MatColumnDef,
    MatRow,
    MatRowDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatHeaderRow,
    MatHeaderRowDef
  ],
  templateUrl: './help.component.html',
  styleUrl: './help.component.scss'
})
export class HelpComponent {
  readonly ref = inject(MatDialogRef<HelpComponent>);
  readonly items: HelpItem[] = [
    {icon: "edit", description: "Edycja wpisu"},
    {icon: "delete", description: "Usuwanie wpisu/ów"},
    {icon: "add", description: "Dodawanie wpisu"},
    {icon: "save", description: "Zapisywanie zaznaczonych wpisów"},
    {icon: "clear", description: "Wyczyść zaznaczenie"},
    {icon: "today", description: "Otwiera kalednarz ustalający zakres dat"}
  ]

  onNoClick() {
    this.ref.close();
  }
}

export interface HelpItem {
  icon: string;
  description: string;
}
