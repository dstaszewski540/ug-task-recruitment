import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ItemBulkDeleteComponent} from './item-bulk-delete.component';

describe('ItemBulkDeleteComponent', () => {
  let component: ItemBulkDeleteComponent;
  let fixture: ComponentFixture<ItemBulkDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItemBulkDeleteComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ItemBulkDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
