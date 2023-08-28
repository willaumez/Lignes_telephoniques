import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeLigneComponent } from './type-ligne.component';

describe('TypeLigneComponent', () => {
  let component: TypeLigneComponent;
  let fixture: ComponentFixture<TypeLigneComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TypeLigneComponent]
    });
    fixture = TestBed.createComponent(TypeLigneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
