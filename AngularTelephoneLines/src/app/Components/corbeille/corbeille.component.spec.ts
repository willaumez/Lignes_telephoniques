import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CorbeilleComponent } from './corbeille.component';

describe('CorbeilleComponent', () => {
  let component: CorbeilleComponent;
  let fixture: ComponentFixture<CorbeilleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CorbeilleComponent]
    });
    fixture = TestBed.createComponent(CorbeilleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
