import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeLigneAddEditComponent } from './type-ligne-add-edit.component';
import {MatDialogModule} from "@angular/material/dialog";

describe('TypeLigneAddEditComponent', () => {
  let component: TypeLigneAddEditComponent;
  let fixture: ComponentFixture<TypeLigneAddEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MatDialogModule],
      declarations: [TypeLigneAddEditComponent]
    });
    fixture = TestBed.createComponent(TypeLigneAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
