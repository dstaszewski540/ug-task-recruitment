import {Component, inject, OnInit} from '@angular/core';
import {Item, Page} from "../../item";
import {HttpClient, HttpParams} from "@angular/common/http";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatFooterCell,
  MatFooterCellDef,
  MatFooterRow,
  MatFooterRowDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from "@angular/material/table";
import {MatError, MatFormField, MatHint, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatCheckbox} from "@angular/material/checkbox";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {SelectionModel} from "@angular/cdk/collections";
import {MatDialog} from "@angular/material/dialog";
import {ItemBulkDeleteComponent} from "../item-bulk-delete/item-bulk-delete.component";
import {ItemFormComponent, ItemFormResult} from "../item-form/item-form.component";
import {ItemDeleteComponent} from "../item-delete/item-delete.component";
import {DatePipe} from "@angular/common";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatSort, MatSortHeader} from "@angular/material/sort";
import {XmlComposer} from "../../xml-composer";
import {MatToolbar} from "@angular/material/toolbar";
import {
  DateRange,
  ExtractDateTypeFromSelection,
  MatDatepickerInputEvent,
  MatDatepickerToggle,
  MatDateRangeInput,
  MatDateRangePicker,
  MatEndDate,
  MatStartDate
} from "@angular/material/datepicker";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {MAT_DATE_LOCALE, provideNativeDateAdapter} from "@angular/material/core";
import {MatProgressSpinner} from "@angular/material/progress-spinner";

@Component({
  selector: 'app-item-list',
  standalone: true,
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'pl-PL'},
    provideNativeDateAdapter()
  ],
  imports: [
    MatHint,
    MatError,
    MatTable,
    MatFormField,
    MatInput,
    MatColumnDef,
    MatHeaderCellDef,
    MatHeaderCell,
    MatCheckbox,
    MatCellDef,
    MatCell,
    MatIcon,
    MatIconButton,
    MatHeaderRow,
    MatRow,
    MatRowDef,
    MatHeaderRowDef,
    MatLabel,
    MatPaginator,
    MatSortHeader,
    MatSort,
    MatToolbar,
    MatDateRangeInput,
    ReactiveFormsModule,
    MatStartDate,
    MatDatepickerToggle,
    MatSuffix,
    MatDateRangePicker,
    MatEndDate,
    DatePipe,
    MatFooterRow,
    MatFooterRowDef,
    MatFooterCell,
    MatFooterCellDef,
    MatProgressSpinner,
    MatButton
  ],
  templateUrl: './item-list.component.html',
  styleUrl: './item-list.component.scss'
})
export class ItemListComponent implements OnInit {
  items = new MatTableDataSource<Item>([])
  selection = new SelectionModel<Item>(true, [])
  dialog = inject(MatDialog)
  params: QueryParams = {
    limit: 20,
    page: 1,
    sort_by: undefined,
    sort_direction: undefined,
    query: undefined,
    start_date: undefined,
    end_date: undefined,
  }
  preload = true;

  readonly client = inject(HttpClient)
  readonly dateRange = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });
  readonly currentDate = new Date(Date.now());

  ngOnInit() {
    this.items.filterPredicate = (data: any, filter): boolean => {
      const query = JSON.parse(filter);
      let isSet = true;
      for (let col in query) {
        switch (col) {
          case "date":
            if (query[col].start || query[col].end) {
              const start = query[col].start ? new Date(Date.parse(query[col].start)) : null
              const end = query[col].end ? new Date(Date.parse(query[col].end)) : null
              const current = new Date(Date.parse(data[col].toString()))

              let startOk = (start) ? current >= start : true;
              let endOk = (end) ? current <= end : true;
              isSet = isSet && (startOk && endOk);
            }
            break;
          case"name":
            let nameValue = !query[col] || data[col].toString().toLowerCase().includes(query[col].toString().trim().toLowerCase());
            isSet = isSet && nameValue;
            break;
        }
      }
      return isSet;
    }
    this.items.filter = JSON.stringify({date: {start: undefined, end: undefined}, name: undefined});
    this.load();
  }

  load() {
    this.preload = true;
    const params = new HttpParams()
    params.set("limit", this.params.limit.toString())
    params.set("page", this.params.page.toString())
    if (this.params.sort_by) {
      params.set("sort_by", this.params.sort_by.toString())
    }
    if (this.params.sort_direction) {
      params.set("sort_direction", this.params.sort_direction.toString())
    }
    if (this.params.query) {
      params.set("query", this.params.query.toString())
    }
    if (this.params.start_date) {
      params.set("start_date", this.params.start_date.toISOString().substring(0, 10))
    }
    if (this.params.end_date) {
      params.set("end_date", this.params.end_date.toISOString().substring(0, 10))
    }
    this.client.get<Page<Item>>("/api/items", {
      params: params,
    }).subscribe({
      next: res => {
        this.items.data = res.content;
        this.preload = false;
      },
      error: err => {
        this.preload = false;
      }
    });
  }

  bulkDelete() {
    if (this.selection.hasValue()) {
      this.dialog.open(ItemBulkDeleteComponent, {
        data: this.selection.selected.length,
      }).afterClosed().subscribe(value => {
        if (value) {
          const count = this.selection.selected.length;
          this.selection.selected.forEach((item, i) => {
            this.client.delete(`/api/items/${item.id}`).subscribe(() => {
              this.selection.toggle(item)
              const idx = this.items.data.indexOf(item);
              if (idx >= 0) {
                this.items.data.splice(idx, 1);
              }
              if (i === count - 1) {
                this.load();
              }
            })
          })
        }
      })
    }
  }

  toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.items.data);
  }

  isAllSelected() {
    return this.selection.selected.length === this.items.data.length;
  }

  checkboxLabel(row ?: Item): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row`;
  }

  toUSD(value: number) {
    return Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
    }).format(value)
  }

  toPLN(value: number) {
    return Intl.NumberFormat('pl-PL', {
      style: 'currency',
      currency: 'PLN',
    }).format(value)
  }

  edit(item: Item) {
    this.dialog.open(ItemFormComponent, {
      maxWidth: "600px",
      width: "400px",
      disableClose: true,
      closeOnNavigation: true,
      data: {
        title: `Edycja "${item.name}"`,
        name: item.name,
        date: item.date,
        usd: item.usd,
        pln: item.pln
      }
    }).afterClosed().subscribe((data: ItemFormResult | undefined) => {
      if (data) {
        this.client.patch<Item>(`/api/items/${item.id}`, data).subscribe(item => {
          this.items.data = this.items.data.map(i => i.id === item.id ? item : i)
        })
      }
    })
  }

  delete(item: Item) {
    this.dialog.open(ItemDeleteComponent, {
      maxWidth: "600px",
      width: "400px",
      disableClose: true,
      closeOnNavigation: true,
      data: item.name,
    }).afterClosed().subscribe((data: boolean) => {
      if (data) {
        this.client.delete(`/api/items/${item.id}`).subscribe(() => {
          this.load()
        })
      }
    })

  }

  private rawStringParse(date: Date | null): string | undefined {
    let v = date
    v?.setUTCHours(0)
    v?.setUTCDate(v?.getUTCDate() + 1)
    return v?.toISOString()?.substring(0, 10);
  }

  filterStartDate(event: MatDatepickerInputEvent<ExtractDateTypeFromSelection<DateRange<Date>>, DateRange<Date>>) {
    const value = this.rawStringParse(event.value);
    const filter = JSON.parse(this.items.filter);
    filter.date.start = value;
    this.items.filter = JSON.stringify(filter);
  }

  filterEndDate(event: MatDatepickerInputEvent<ExtractDateTypeFromSelection<DateRange<Date>>, DateRange<Date>>) {
    const value = this.rawStringParse(event.value);
    const filter = JSON.parse(this.items.filter);
    filter.date.end = value;
    this.items.filter = JSON.stringify(filter);
  }

  filterQuery(event: KeyboardEvent) {
    const value = (event.target as HTMLInputElement).value;
    const filter = JSON.parse(this.items.filter);
    filter.name = value;
    this.items.filter = JSON.stringify(filter);
  }

  xmlExport() {
    if (this.selection.hasValue()) {
      XmlComposer.export(this.selection.selected)
    }
  }

  loadPage(event: PageEvent) {
    this.params.page = event.pageIndex + 1
    this.params.limit = event.pageSize
    this.load()
  }

  sumUSD() {
    if (this.selection.hasValue()) {
      return this.toUSD(this.selection.selected.reduce((acc, item) => acc + item.usd, 0))
    }
    return this.toUSD(this.items.data.reduce((acc, item) => acc + item.usd, 0));
  }

  sumPLN() {
    if (this.selection.hasValue()) {
      return this.toPLN(this.selection.selected.reduce((acc, item) => acc + item.pln, 0))
    }
    return this.toPLN(this.items.data.reduce((acc, item) => acc + item.pln, 0));
  }

  add() {
    this.dialog.open(ItemFormComponent, {
      maxWidth: "600px",
      width: "400px",
      disableClose: true,
      closeOnNavigation: true,
      data: {
        title: "Nowy wpis"
      }
    },).afterClosed().subscribe((data: ItemFormResult | undefined) => {
      if (data) {
        this.client.post<Item>("/api/items", data).subscribe(() => {
          this.load()
        })
      }
    })
  }
}

export interface QueryParams {
  limit: number;
  page: number;
  start_date: Date | undefined;
  end_date: Date | undefined;
  query: string | undefined;
  sort_by: "name" | "date" | undefined;
  sort_direction: "ASC" | "DESC" | undefined;
}
