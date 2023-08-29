import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeLigneAddEditComponent } from './type-ligne-add-edit.component';

describe('TypeLigneAddEditComponent', () => {
  let component: TypeLigneAddEditComponent;
  let fixture: ComponentFixture<TypeLigneAddEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
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
