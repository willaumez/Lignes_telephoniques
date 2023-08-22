import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeAttributComponent } from './type-attribut.component';

describe('TypeAttributComponent', () => {
  let component: TypeAttributComponent;
  let fixture: ComponentFixture<TypeAttributComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TypeAttributComponent]
    });
    fixture = TestBed.createComponent(TypeAttributComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
