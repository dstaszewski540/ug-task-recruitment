import {Component, inject, signal} from '@angular/core';
import {
    MAT_DIALOG_DATA,
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogRef,
    MatDialogTitle
} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatError, MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {Rate} from "../../item";
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from "@angular/material/datepicker";
import {MAT_DATE_LOCALE, provideNativeDateAdapter} from "@angular/material/core";

@Component({
    selector: 'app-item-form',
    standalone: true,
    providers: [
        {provide: MAT_DATE_LOCALE, useValue: 'pl-PL'},
        provideNativeDateAdapter(),
    ],
    imports: [
        MatDialogTitle,
        MatDialogContent,
        MatDialogActions,
        MatButton,
        MatDialogClose,
        ReactiveFormsModule,
        MatFormField,
        MatInput,
        MatError,
        MatLabel,
        NgIf,
        MatDatepickerInput,
        MatDatepickerToggle,
        MatDatepicker,
        FormsModule,
        MatSuffix
    ],
    templateUrl: './item-form.component.html',
    styleUrl: './item-form.component.scss'
})
export class ItemFormComponent {
    readonly ref = inject(MatDialogRef<ItemFormComponent>);
    readonly data = inject<ItemFormData>(MAT_DIALOG_DATA);
    readonly form = new FormGroup({
        name: new FormControl(this.data.name, Validators.required),
        date: new FormControl(this.data.date, [Validators.required, Validators.max(Date.now())]),
        usd: new FormControl(this.data.usd, [Validators.required, Validators.min(0.01)])
    });
    readonly client = inject(HttpClient)
    readonly pln = signal(this.data.pln || 0)
    readonly currentDate = new Date(Date.now());

    update() {
        const value = this.form.get('usd')?.value;
        const date = this.form.get('date')?.value
        if (value && date) {
            this.reload(value, date)
        }
    }

    onNoClick() {
        this.ref.close();
    }

    result(): ItemFormResult | undefined {
        if (this.form.valid) {
            return {
                name: this.form.get('name')?.value || undefined,
                date: this.form.get('date')?.value || undefined,
                price: this.form.get('usd')?.value || undefined,
            }
        }
        return undefined;
    }

    private reload(value: number, date: Date) {
        const rawDate = date.toISOString().substring(0, 10);
        this.client.get<Rate>(`http://localhost:8080/api/exchange/USD/${rawDate}`).subscribe(res => {
            const calc = Math.round((res.bid * 100) * (value || 0.0));
            this.pln.set(calc / 100)
        })
    }
}

export interface ItemFormData {
    title: string;
    name: string | undefined;
    date: Date | undefined;
    usd: number | undefined;
    pln: number | undefined;
}

export interface ItemFormResult {
    name: string | undefined;
    date: Date | undefined;
    price: number | undefined;
}
