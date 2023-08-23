import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionTypeComponent } from './gestion-type.component';

describe('GestionTypeComponent', () => {
  let component: GestionTypeComponent;
  let fixture: ComponentFixture<GestionTypeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GestionTypeComponent]
    });
    fixture = TestBed.createComponent(GestionTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
