import {Component} from '@angular/core';
import {ItemListComponent} from "../../parts/item-list/item-list.component";

@Component({
  selector: 'app-view',
  standalone: true,
  imports: [
    ItemListComponent
  ],
  templateUrl: './view.component.html',
  styleUrl: './view.component.scss'
})
export class ViewComponent {

}
